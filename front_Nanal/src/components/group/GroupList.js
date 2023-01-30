import React, { useState, useEffect } from 'react';
import axios_api from '../../config/Axios';
import GroupItem from './GroupItem';

function GroupList() {
  const [groupList, setGroupList] = useState([]);

  useEffect(() => {
    axios_api
      .get('group/list/1')
      .then(({ data }) => {
        if (data.statusCode === 200) {
          setGroupList(null);
          if (data.data.responseMessage === '그룹 리스트 조회 성공') {
            setGroupList(data.data.groupList);
          }
        } else {
          console.log(data.statusCode);
          console.log(data.data.ResponseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('그룹 리스트 불러오기 오류: ' + error);
      });
  }, []);

  return (
    <div>
      <h1>그룹 리스트</h1>
      {groupList.map((groupItem) => (
        <GroupItem key={groupItem.groupDetail} item={groupItem} />
      ))}
    </div>
  );
}

export default GroupList;
