import { useState, useEffect, useRef } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios_api from "../../config/Axios";
import { onLogin } from "../../config/Login";
import { BookFilled, BookOutlined } from "@ant-design/icons";

function DiaryDetail() {
  const location = useLocation();
  const [diaryDetail, setDiaryDetail] = useState({});
  const [group, setGroup] = useState("개인");

  // 삭제 버튼 클릭 시 URL 이동
  const navigate = useNavigate();
  // 일기 수정
  const [isEdit, setIsEdit] = useState(false);
  const toggleIsEdit = () => {
    setIsEdit(!isEdit);
    setLocalContent(diaryDetail.content);
    setLocalDate(location.state.diaryDate);
  };
  // 수정된 날짜 데이터
  const [localDate, setLocalDate] = useState(location.state.diaryDate);
  // 수정된 일기 데이터
  const [localContent, setLocalContent] = useState("");
  const localConetRef = useRef();
  // 기존 그룹 리스트 데이터
  const [originGroupList, setOriginGroupList] = useState();

  const handleQuitEdit = () => {
    setIsEdit(false);
    setLocalContent(diaryDetail.content);
    setLocalDate(location.state.diaryDate);
  };
  // 북마크 여부 데이터
  const [isBook, setIsBook] = useState(false);

  // 일기 상세 페이지 불러오기
  useEffect(() => {
    onLogin();
    axios_api
      .get(`diary/${location.state.diaryIdx}`)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === "일기 조회 성공") {
            setDiaryDetail(data.data.diary); // 데이터는 response.data.data 안에 들어있음
            setOriginGroupList(data.data.groupList);
            setCheckedList(data.data.groupList);
            if (data.data.groupList.length !== 0) {
              setGroup("그룹");
              setShow(true);
            }

            if (data.data.isBookmark === true) {
              setIsBook(!isBook);
            }
          }
        } else {
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch((err) => console.log(err));
  }, []);

  // 체크된 그룹을 넣어줄 배열
  const [checkedList, setCheckedList] = useState([]);

  // input 태그가 체크된 경우 실행되는 함수
  const onChecked = (checked, id) => {
    if (checked) {
      setCheckedList([...checkedList, id]);
    } else {
      setCheckedList(checkedList.filter((el) => el !== id));
    }
  };

  // 그룹 리스트 데이터 가져오기
  const [groupList, setGroupList] = useState([]);
  useEffect(() => {
    onLogin();
    axios_api
      .get("group/list")
      .then(({ data }) => {
        if (data.statusCode === 200) {
          setGroupList(null);
          if (data.data.responseMessage === "그룹 리스트 조회 성공") {
            setGroupList(data.data.groupList);
          }
        } else {
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log("그룹 리스트 불러오기 오류: " + error);
      });
  }, []);

  // 그룹 리스트 보여줄지 말지
  const [isShow, setShow] = useState(false);

  return (
    <div>
      <div className="flex justify-between">
        <span>그림 들어갈 자리</span>
        {isBook ? (
          <>
            <BookFilled
              onClick={() =>
                axios_api
                  .delete(`diary/bookmark/${location.state.diaryIdx}`)
                  .then(({ data }) => {
                    if (data.statusCode === 200) {
                      if (
                        data.data.responseMessage === "일기 북마크 삭제 성공"
                      ) {
                        setIsBook(!isBook);
                      }
                    } else {
                      console.log(data.statusCode);
                      console.log(data.data.responseMessage);
                    }
                  })
              }
            />
          </>
        ) : (
          <>
            <BookOutlined
              onClick={() =>
                axios_api
                  .get(`diary/bookmark/${location.state.diaryIdx}`)
                  .then(({ data }) => {
                    if (data.statusCode === 200) {
                      if (
                        data.data.responseMessage === "일기 북마크 저장 성공"
                      ) {
                        setIsBook(!isBook);
                      }
                    } else {
                      console.log(data.statusCode);
                      console.log(data.data.responseMessage);
                    }
                  })
              }
            />
          </>
        )}
      </div>
      <div>작성자 프로필 사진 | </div>
      {/* <span>{diaryDetail.nickname}</span> */}
      <div>
        {isEdit ? (
          <>
            <button className="border-rose-500" onClick={handleQuitEdit}>
              수정 취소
            </button>
            <button
              onClick={() => {
                axios_api
                  .put("diary", {
                    userIdx: diaryDetail.userIdx,
                    diaryIdx: diaryDetail.diaryIdx,
                    content: localContent,
                    diaryDate: localDate,
                    groupIdxList: checkedList,
                  })
                  .then(({ data }) => {
                    setDiaryDetail(data.data.diary);
                    setIsEdit(false);
                  })
                  .catch((err) => console.log(err));
              }}
            >
              수정 완료
            </button>
          </>
        ) : (
          <>
            <button onClick={toggleIsEdit}>수정</button>
            <button
              onClick={() => {
                if (
                  window.confirm(
                    `${diaryDetail.diaryIdx}번째 일기를 정말 삭제하시겠습니까?`
                  )
                ) {
                  axios_api
                    .delete(`diary/${location.state.diaryIdx}`)
                    .then(({ data }) => {
                      if (data.statusCode === 200) {
                        if (data.data.responseMessage === "일기 삭제 성공") {
                          setDiaryDetail(data.data.diary);
                          navigate("/", { replace: true });
                        }
                      } else {
                        console.log(data.statusCode);
                        console.log(data.data.responseMessage);
                      }
                    });
                }
              }}
            >
              삭제
            </button>
          </>
        )}
      </div>
      <div>
        {isEdit ? (
          <>
            <h2>일기 수정하기</h2>
            {/* 날짜 선택란 */}
            <div>
              <input
                value={localDate}
                onChange={(e) => setLocalDate(e.target.value)}
                type="date"
              />
            </div>
            {/* 일기 내용 작성란 */}
            <div>
              <textarea
                className="mb-10 w-full h-min"
                name="content"
                ref={localConetRef}
                value={localContent}
                onChange={(e) => {
                  setLocalContent(e.target.value);
                }}
              />
            </div>
            {/* 그룹 여부 선택란 */}
            <div>
              <input
                type="radio"
                value="개인"
                checked={group === "개인"}
                onChange={(e) => setGroup(e.target.value)}
                onClick={() => setShow(false)}
              />
              <label>개인</label>
              <input
                type="radio"
                value="그룹"
                checked={group === "그룹"}
                onChange={(e) => setGroup(e.target.value)}
                onClick={() => setShow(true)}
              />
              <label>그룹</label>
              {isShow ? (
                <>
                  {groupList.map((groupItem, idx) => {
                    return (
                      <div
                        key={idx}
                        className="bg-[#F7F7F7] border-2 border-solid border-slate-400 rounded-lg m-1 mb-3 p-2"
                      >
                        <label htmlFor={groupItem.groupDetail.groupIdx}>
                          {groupItem.groupDetail.groupName}
                        </label>
                        <input
                          type="checkbox"
                          id={groupItem.groupDetail.groupIdx}
                          checked={
                            checkedList.includes(groupItem.groupDetail.groupIdx)
                              ? true
                              : false
                          }
                          onChange={(e) => {
                            onChecked(
                              e.target.checked,
                              groupItem.groupDetail.groupIdx
                            );
                          }}
                        />
                      </div>
                    );
                  })}
                </>
              ) : (
                <></>
              )}
            </div>
          </>
        ) : (
          <>
            <div className="text-sm">{diaryDetail.diaryDate}</div>
            {diaryDetail.content}
          </>
        )}
      </div>
    </div>
  );
}

DiaryDetail.defaultProps = {
  diaryDetail: [],
};

export default DiaryDetail;
