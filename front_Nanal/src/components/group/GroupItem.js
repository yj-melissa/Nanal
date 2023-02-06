import React from 'react';
import { Link } from 'react-router-dom';

function GroupItem({ item }) {
  return (
    <div>
      <Link
        to={`/Group/${item.groupDetail.groupIdx}`}
        state={{ groupIdx: item.groupDetail.groupIdx }}
      >
        <div className='bg-[#F7F7F7] border-2 border-solid border-slate-400 rounded-lg m-1 mb-3 p-2'>
          <p className='font-bold mb-0.5'>{item.groupDetail.groupName}</p>
          {item.tags.map((tagging, idx) => {
            if (tagging.tag) return <span key={idx}>#{tagging.tag}&nbsp;</span>;
            // return tagging.tag ? <span key={idx}>#{tagging.tag}</span> : <></>;
          })}
        </div>
      </Link>
    </div>
  );
}

export default GroupItem;
