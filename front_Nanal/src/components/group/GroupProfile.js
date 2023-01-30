import React, { useState } from 'react';
import axios_api from '../../config/Axios';

function GroupProfile() {
  const [groupName, setGroupName] = useState('');
  const [groupTag, setGroupTag] = useState([]);
  const [tagNum, setTagNum] = useState(0);
  let [tagNew, setTagNew] = useState('');

  const onChageName = (e) => {
    setGroupName(e.target.value);
  };

  function addTag(e) {
    e.preventDefault();
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

  const GroupCreate = (e) => {
    e.preventDefault();
    axios_api
      .post('/group', {
        groupName: groupName,
        tags: groupTag,
      })
      .then(({ data }) => {
        console.log(data.statusCode);
        if (data.statusCode === 200) {
          if (data.data.ResponseMessage === '그룹 생성 성공') {
            console.log(data.data.groupDetail);
            console.log(data.data.tags);
            // window.location.replace("/");
          }
        } else {
          console.log(data.statusCode);
          console.log(data.data.ResponseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('그룹 생성 성공: ' + error);
      });
  };

  return (
    <div>
      <form onSubmit={GroupCreate}>
        <div id='group-name-div'>
          <label htmlFor='group-name'>그룹 이름 : </label>
          <input
            type='text'
            id='group-name'
            className='font-bold m-0.5'
            onChange={onChageName}
          ></input>
        </div>
        <div id='group-tag-div'>
          <label htmlFor='group-tag'>그룹 태그 : (5개까지 가능)</label>
          <input
            type='text'
            id='group-tag'
            onChange={onChageTagNew}
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
        <button type='submit'>생성</button>
      </form>
    </div>
  );
}

export default GroupProfile;
