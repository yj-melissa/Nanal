import React, { useState } from 'react';

function GroupProfile(params) {
  const [groupName, setGroupName] = useState('');
  const [groupTag, setGroupTag] = useState(['짜장면', '짬뽕', '울면']);
  const [tagNum, setTagNum] = useState(0);
  let [tagNew, setTagNew] = useState('');

  const onChageName = (e) => {
    setGroupName(e.target.value);
  };

  function addTag() {
    if (groupTag.length === 5) {
      alert('태그는 5개까지만 가능합니다.');
      setTagNew('');
    } else if (tagNew !== '') {
      let tagList = [...groupTag];
      tagList.push(tagNew);
      setGroupTag(tagList);
      setTagNew('');
    }
  }

  const onChageTagNew = (e) => {
    setTagNew(e.target.value);
  };

  const onChageTagRemove = (id) => {
    // tag.id 가 파라미터로 일치하지 않는 원소만 추출해서 새로운 배열을 만듬
    // = tag.id 가 id 인 것을 제거함
    let tagList = [...groupTag];
    tagList.splice(id, 1);
    setGroupTag(tagList);
  };

  return (
    <div>
      <label htmlFor='group-name'>그룹 이름 : </label>
      <input
        type='text'
        id='group-name'
        className='font-bold m-0.5'
        onChange={onChageName}
      ></input>
      <br />
      <div>
        <label htmlFor='group-tag'>그룹 태그 : (5개까지 가능)</label>
        <input
          type='text'
          id='group-tag'
          onChange={onChageTagNew}
          onKeyPress={addTag}
          value={tagNew}
        />
        &nbsp;
        <button onClick={addTag}>추가</button>
        <br />
        {groupTag.map((tagging, idx) => {
          return (
            <button
              onClick={() => {
                setTagNum(idx);
                onChageTagRemove(idx);
              }}
              key={idx}
            >
              #{tagging}&nbsp;
            </button>
          );
        })}
      </div>
    </div>
  );
}

export default GroupProfile;
