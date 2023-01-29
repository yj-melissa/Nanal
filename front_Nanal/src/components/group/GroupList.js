import React, { useState } from 'react';
import axios_api from '../../config/Axios';
import GroupItem from './GroupItem';

function GroupList() {
  const [groupList, setGroupList] = useState([
    {
      groupDetail: {
        groupIdx: 25,
        groupName: 'string',
        groupImg: 'string',
        creationTime: '2023-01-29T07:52:34.264+00:00',
        private: true,
      },
      tags: [
        {
          tagIdx: 71,
          tag: 'string',
        },
        {
          tagIdx: 72,
          tag: 'x',
        },
        {
          tagIdx: 73,
          tag: 'x',
        },
        {
          tagIdx: 74,
          tag: 'x',
        },
        {
          tagIdx: 75,
          tag: '',
        },
      ],
    },
    {
      groupDetail: {
        groupIdx: 26,
        groupName: 'string',
        groupImg: 'string',
        creationTime: '2023-01-29T07:53:21.501+00:00',
        private: true,
      },
      tags: [
        {
          tagIdx: 76,
          tag: 'string',
        },
        {
          tagIdx: 77,
          tag: 'x',
        },
        {
          tagIdx: 78,
          tag: 'x',
        },
        {
          tagIdx: 79,
          tag: 'x',
        },
        {
          tagIdx: 80,
          tag: '',
        },
      ],
    },
  ]);

  return (
    <div>
      <h1>그룹 리스트</h1>
      {groupList.map((groupItem) => (
        <GroupItem key={groupItem.groupDetail.groupIdx} item={groupItem} />
      ))}
    </div>
  );
}

export default GroupList;
