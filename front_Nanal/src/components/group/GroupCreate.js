import React, { useState } from 'react';
import axios_api from '../../config/Axios';
import GroupProfile from './GroupProfile';
import GroupFriend from './GroupFriend';

function GroupCreate() {
  const [groupData, setGroupData] = useState({});
  const [groupFreindData, setGroupFreindData] = useState([]);
  const [groupToggle, setGroupToggle] = useState(false);

  const changeToggle = (data) => {
    setGroupToggle(data);
  };
  const changeFData = (data) => {
    setGroupData(data);
  };

  const changeData = (data) => {
    console.log(data);
    setGroupData(data);

    if (groupData.tags.length !== 5) {
      const len = 5 - groupData.tags.length;
      for (let i = 0; i < len; i++) {
        groupData.tags.push('');
      }
    }

    // axios_api
    //   .post('/group', {
    //     groupData,
    //   })
    //   .then(({ data }) => {
    //     if (data.statusCode === 200) {
    //       if (data.data.responseMessage === '그룹 생성 성공') {
    //         console.log(data.data.groupDetail);
    //         console.log(data.data.tags);
    //         // window.location.replace("/");
    //       }
    //     } else {
    //       console.log(data.statusCode);
    //       console.log(data.data.responseMessage);
    //     }
    //   })
    //   .catch(({ error }) => {
    //     console.log('hihiasdf');
    //     console.log('그룹 생성 성공: ' + error);
    //   });
  };

  return (
    <div>
      <h2> 그룹 생성 </h2>
      <GroupProfile changeData={changeData} toggle={groupToggle} />

      <GroupFriend changeData={changeFData} toggle={changeToggle} />
    </div>
  );
}

export default GroupCreate;
