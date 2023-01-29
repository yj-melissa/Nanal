import React from 'react';

const InputBox = () => (
  <div id="inputbox">
    {/* 아이템 내용 입력 input */}
    <input
      type="text"
      placeholder="자신을 표현해주세요."
      id="inputbox-inp"
    />
    {/* 입력 후 아이템 추가 버튼 */}
    <button type="submit" id="inputbox-add-btn">
      추가
    </button>
  </div>
);

export default InputBox;