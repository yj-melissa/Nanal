import React from 'react';

function GroupItem({ item }) {
  console.log(item);
  console.log(item.groupDetail.groupIdx);
  console.log(item.tags);
  return (
    <div>
      {item.groupDetail.groupIdx}, {item.tags[0].tag}
    </div>
  );
}

export default GroupItem;
