import React, { useState, useEffect } from 'react';
import axios_api from '../../config/Axios';
import GroupItem from './GroupItem';
import { onLogin } from '../../config/Login';
import { Link } from 'react-router-dom';

function GroupList() {
  const [groupList, setGroupList] = useState([]);

  useEffect(() => {
    onLogin();
    axios_api
      .get('group/list')
      .then(({ data }) => {
        if (data.statusCode === 200) {
          setGroupList(null);
          if (data.data.responseMessage === '그룹 리스트 조회 성공') {
            setGroupList(data.data.groupList);
          }
        } else {
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('그룹 리스트 불러오기 오류: ' + error);
      });
  }, []);

  return (
    <div>
      <h1>그룹 리스트</h1>
      <Link to='/Group/Create'>
        <button>그룹 생성하기</button>
      </Link>
      {groupList.map((groupItem) => (
        <GroupItem key={groupItem.groupDetail.groupIdx} item={groupItem} />
      ))}
    </div>
  );
}

export default GroupList;
