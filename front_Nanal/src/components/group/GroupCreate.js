import React, { useState } from 'react';
import GroupProfile from './GroupProfile';
import GroupFriend from './GroupFriend';

function GroupCreate() {
  const [groupData, setGroupData] = useState({});

  return (
    <div>
      <h2> 그룹 생성 </h2>
      <GroupProfile />
      <hr className='border-solid border-1 border-slate-800 w-80 my-5' />
      <GroupFriend />
    </div>
  );
}

export default GroupCreate;
