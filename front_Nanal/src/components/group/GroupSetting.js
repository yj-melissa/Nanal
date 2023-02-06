import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router';
import { Link } from 'react-router-dom';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';
import FriendItem from '../friend/FriendItem';
import emo_joy from '../../src_assets/img/emo_joy.png';

function GroupSetting() {
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

            const groupidx = data.data.groupDetail.groupIdx;

            axios_api
              .get(`group/user/${groupidx}`)
              .then(({ data }) => {
                if (data.statusCode === 200) {
                  setFriendList(null);
                  if (data.data.responseMessage === '그룹 유저 조회 성공') {
                    setFriendList(data.data.groupUserList);
                  } else if (data.data.responseMessage === '데이터 없음') {
                    setFriendList(['아직은 친구가 없습니다.']);
                  }
                } else {
                  console.log('그룹 유저 조회 오류: ');
                  console.log(data.statusCode);
                  console.log(data.data.responseMessage);
                }
              })
              .catch(({ error }) => {
                console.log('그룹 유저 조회 오류: ' + error);
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
      {/* <h1 className='text-center'>그룹 상세 페이지</h1> */}

      <div className='w-full my-2'>
        <div className='my-3'>
          {groupDetail.imgUrl !== null ? (
            <img
              src={groupDetail.imgUrl}
              className='inline-block p-1 rounded-md w-28 h-28'
            ></img>
          ) : (
            <>
              <img
                src={emo_joy}
                className='inline-block p-1 rounded-md w-28 h-28'
              ></img>
            </>
          )}

          <p className='inline-block ml-5 text-2xl font-bold text-center'>
            {groupDetail.groupName}
          </p>
        </div>
        <div>
          {groupTag.map((tagging, idx) => {
            return <span key={idx}>#{tagging.tag}&nbsp;</span>;
          })}
        </div>
      </div>
      <Link
        to={`/Group/Update/${groupDetail.groupIdx}`}
        state={{ groupDetail: groupDetail.groupIdx }}
      >
        <button className='bg-cyan-600 text-white px-2.5 py-1 rounded-3xl m-auto block'>
          수정하기
        </button>
      </Link>

      <hr className='my-5 border-solid border-1 border-slate-800 w-80' />

      {/* {friendList.map((friendItem, idx) => {
        return (
          <Link
            key={idx}
            to={`/Friend/${friendItem.userIdx}`}
            state={{ friendIdx: friendItem.userIdx }}
          >
            <div>
              {friendItem.img}
              <br />
              {friendItem.nickName}
              <br />
              {friendItem.introduction}
            </div>
          </Link>
        );
      })} */}
      {friendList[0] === '아직은 친구가 없습니다.' ? (
        <p>아직은 그룹에 친구가 없습니다.</p>
      ) : (
        friendList.map((friendItem, idx) => (
          <FriendItem key={idx} item={friendItem} />
        ))
      )}
    </div>
  );
}

export default GroupSetting;
