import React, { useState, useEffect } from 'react';
import axios_api from '../../config/Axios';
import { onLogin } from '../../config/Login';
import { Link } from 'react-router-dom';
import GroupDetail from './GroupDetail';
import addIcon from '../../src_assets/img/group_add_icon.png';

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
    <div id='group-list'>
      <div id='group-list-title' className='mb-3'>
        <h1 className='m-1 font-bold inline'>그룹 리스트</h1>
        {/* <Link to='/Group/Create'>
        <button>그룹 생성하기</button>
      </Link> */}
        <Link to='/Group/Create' className='inline-block float-right'>
          <img src={addIcon} className='w-[22px] h-[22px] mx-1.5' />
        </Link>
      </div>
      {groupList.map((groupItem) => (
        <GroupDetail key={groupItem.groupDetail.groupIdx} item={groupItem} />
      ))}
    </div>
  );
}

export default GroupList;
