import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router';
import { Link } from 'react-router-dom';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';

function GroupProfile() {
  const { state } = useLocation();

  const [groupDetail, setGroupName] = useState('');
  const [groupTag, setGroupTag] = useState([]);

  const [friendList, setFriendList] = useState([{}]);

  useEffect(() => {
    onLogin();
    axios_api
      .get(`/group/${state.groupIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          setGroupName(null);
          setGroupTag(null);
          if (data.data.responseMessage === '그룹 조회 성공') {
            setGroupName(data.data.groupDetail);
            setGroupTag(data.data.tags);

            axios_api
              .get(`friend/list`)
              .then(({ data }) => {
                if (data.statusCode === 200) {
                  setFriendList(null);
                  if (data.data.responseMessage === '친구 리스트 조회 성공') {
                    setFriendList(data.data.friendList);
                  } else if (data.data.responseMessage === '데이터 없음') {
                    setFriendList(['아직은 친구가 없습니다.']);
                  }
                } else {
                  console.log('친구 리스트 조회 오류: ');
                  console.log(data.statusCode);
                  console.log(data.data.responseMessage);
                }
              })
              .catch(({ error }) => {
                console.log('친구 리스트 조회 오류: ' + error);
              });
          }
        } else {
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('그룹 상세 보기 오류: ' + error);
      });
  }, []);

  return (
    <div>
      <h1>그룹 상세 페이지</h1>

      <div>
        <p>{groupDetail.groupName}</p>
        <button>수정하기</button>

        {groupTag.map((tagging, idx) => {
          return <span key={idx}>#{tagging.tag}&nbsp;</span>;
        })}
      </div>

      {friendList.map((friendItem, idx) => {
        return (
          <Link
            key={idx}
            to={`/Friend/${friendItem.userIdx}`}
            state={{ friendIdx: friendItem.userIdx }}
          >
            <div>
              {friendItem.img}
              <br />
              {friendItem.nickname}
              <br />
              {friendItem.introduction}
            </div>
          </Link>
        );
      })}
    </div>
  );
}

export default GroupProfile;
