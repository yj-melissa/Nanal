import { useState, useRef } from "react";

function DiaryItem({ id, content, group, created_at, onRemove, onEdit }) {
  // 포커스 기능을 할 객체 생성
  const localContentInput = useRef();
  // 삭제하기 클릭 시 실행되는 함수
  const handleRemove = () => {
    if (window.confirm(`${id}번째 일기를 정말 삭제하시겠습니까?`)) {
      onRemove(id);
    }
  };

  // 수정여부 확인
  const [isEdit, setIsEdit] = useState(false);
  // 현재값을 반대로 토글해주는 함수
  const toggleIsEdit = () => setIsEdit(!isEdit);
  // 수정한 데이터 확인
  const [localContent, setLocalContent] = useState(content);
  // 수정 취소 버튼 클릭 시 데이터 초기화 함수 (수정 > 취소 > 수정 시 데이터 그대로 존재)
  const handleQuitEdit = () => {
    setIsEdit(false);
    setLocalContent(content);
  };
  // 수정 완료 버튼 클릭 시 함수
  const handleEdit = () => {
    if (localContent.length < 2) {
      localContentInput.current.focus();
      return;
    }
    if (window.confirm(`${id}번째 일기를 수정하시겠습니까?`)) {
      onEdit(id, localContent);
      toggleIsEdit();
    }
  };

  return (
    <div className="DiaryItem">
      <div className="info">
        <span>일기 번호 : {id}</span>
        <br />
        <span className="date">
          작성 시간 : {new Date(created_at).toLocaleDateString()}
        </span>
        <br />
        <span className="group">공개 여부 : {group}</span>
      </div>
      <div className="content">
        {isEdit ? (
          <>
            <textarea
              ref={localContentInput}
              value={localContent}
              onChange={(e) => setLocalContent(e.target.value)}
            />
          </>
        ) : (
          <>{content}</>
        )}
      </div>
      {/* 수정 상태에 따라 버튼도 다르게 표현 */}
      {isEdit ? (
        <>
          <button onClick={handleQuitEdit}>수정취소</button>
          <button onClick={handleEdit}>수정완료</button>
        </>
      ) : (
        <>
          <button onClick={handleRemove}>삭제하기</button>
          <button onClick={toggleIsEdit}>수정하기</button>
        </>
      )}
    </div>
  );
}

export default DiaryItem;
