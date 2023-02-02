import { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios_api from "../../config/Axios";
import Comment from "./Comment";

function DiaryDetail() {
  const location = useLocation();
  const [diaryDetail, setDiaryDetail] = useState({});

  // 작성 일자 표현법
  const strDate = new Date(diaryDetail.creationDate).toLocaleString();
  // 삭제 버튼 클릭 시 URL 이동
  const navigate = useNavigate();
  // 일기 수정
  const [isEdit, setIsEdit] = useState(false);
  const toggleIsEdit = () => {
    setIsEdit(!isEdit);
    setLocalContent(diaryDetail.content);
  };
  // 수정된 데이터
  const [localContent, setLocalContent] = useState("");

  const handleQuitEdit = () => {
    setIsEdit(false);
    setLocalContent(diaryDetail.content);
  };
  // 북마크 여부 데이터
  const [isBook, setIsBook] = useState(false);

  // 일기 상세 페이지 불러오기
  useEffect(() => {
    axios_api.get(`diary/${location.state.diaryIdx}`).then(({ data }) => {
      if (data.statusCode === 200) {
        if (data.data.responseMessage === "일기 조회 성공") {
          setDiaryDetail(data.data.diary); // 데이터는 response.data.data 안에 들어있음
          if (data.data.isBookmark === true) {
            setIsBook(!isBook);
          }
        }
      } else {
        console.log(data.statusCode);
        console.log(data.data.responseMessage);
      }
    });
  }, []);

  return (
    <div>
      <span>그림 들어갈 자리</span>
      {isBook ? (
        <>
          <button
            onClick={() =>
              axios_api
                .delete(`diary/bookmark/${location.state.diaryIdx}`)
                .then(({ data }) => {
                  if (data.statusCode === 200) {
                    if (data.data.responseMessage === "일기 북마크 삭제 성공") {
                      setIsBook(!isBook);
                    }
                  } else {
                    console.log(data.statusCode);
                    console.log(data.data.responseMessage);
                  }
                })
            }
          >
            칠한 북마크
          </button>
        </>
      ) : (
        <>
          <button
            onClick={() =>
              axios_api
                .get(`diary/bookmark/${location.state.diaryIdx}`)
                .then(({ data }) => {
                  if (data.statusCode === 200) {
                    if (data.data.responseMessage === "일기 북마크 저장 성공") {
                      setIsBook(!isBook);
                    }
                  } else {
                    console.log(data.statusCode);
                    console.log(data.data.responseMessage);
                  }
                })
            }
          >
            빈 북마크
          </button>
        </>
      )}
      <div>작성자 프로필 사진 | </div>
      <span>user nickname | </span>
      <div>작성 시간 : {strDate}</div>
      <div>
        {isEdit ? (
          <>
            <button onClick={handleQuitEdit}>수정 취소</button>
            <button
              onClick={() => {
                axios_api
                  .put("diary", {
                    // userIdx: 1,
                    groupIdxList: [1],
                    diaryIdx: diaryDetail.diaryIdx,
                    content: localContent,
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
                          setDiaryDetail(data.data.diary); // 데이터는 response.data.data 안에 들어있음
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
            <textarea
              value={localContent}
              onChange={(e) => setLocalContent(e.target.value)}
            />
          </>
        ) : (
          <>{diaryDetail.content}</>
        )}
      </div>
      <div>
        <Comment
          diaryIdx={diaryDetail.diaryIdx}
          groupIdx={diaryDetail.groupIdx}
        />
      </div>
    </div>
  );
}

DiaryDetail.defaultProps = {
  diaryDetail: [],
};

export default DiaryDetail;
