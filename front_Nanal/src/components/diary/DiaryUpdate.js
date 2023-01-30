import { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios_api from "../../config/Axios";

function DiaryUpdate() {
  const location = useLocation();
  console.log(location);
  const [diaryEdit, setDiaryEdit] = useState();
  // 뒤로 가기 기능
  const navigate = useNavigate();

  useEffect(() => {
    axios_api
      .put(``)
      .then(({ data }) => {
        if (data.statusCode === 200) {
          setDiaryEdit();
          if (data.data.responseMessage === "일기 수정 성공 ") {
            setDiaryEdit(data.data.diary);
          }
        } else {
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ err }) => {
        console.log("일기 수정 페이지 불러오기 오류: ", err);
      });
  }, []);

  return (
    <div>
      <h2>수정페이지</h2>
      <div>
        <button onClick={() => navigate(-1)}>수정 취소</button>
        <button
          onClick={() => {
            if (
              window.confirm(
                `${diaryEdit.diaryIdx}번째 일기를 수정하시겠습니까?`
              )
            ) {
              axios_api.put(``).then(({ data }) => {
                if (data.statusCode === 200) {
                  if (data.data.responseMessage === "일기 수정 성공") {
                    setDiaryEdit(data.data.diary);
                    navigate("", { replace: true });
                  }
                } else {
                  console.log(data.statusCode);
                  console.log(data.data.responseMessage);
                }
              });
            }
          }}
        >
          수정 완료
        </button>
      </div>
    </div>
  );
}

export default DiaryUpdate;
