import { useState, useEffect } from 'react';
import axios_api from '../../config/Axios';

function FavoriteList() {
  // 좋아하는 일기 데이터 받기
  const [favoriteDiary, setFavoriteDiary] = useState([]);

  useEffect(() => {
    axios_api.get('diary/bookmark').then(({ data }) => {
      if (data.statusCode === 200) {
        setFavoriteDiary(null);
        if (data.data.responseMessage === '일기 북마크 리스트 조회 성공') {
          setFavoriteDiary(data.data.dairy);
        }
      } else {
        console.log(data.statusCode);
        console.log(data.data.responseMessage);
      }
    });
  }, []);

  return (
    <div>
      <h4>좋아하는 일기 개수는 총 {favoriteDiary.length}개 입니다.</h4>
      {/* <div>
        {favoriteDiary.map((pictures, idx) => (
          <FavoriteItem key={idx} {...pictures} />
        ))}
      </div> */}
      <div>그림 들어갈 자리</div>
    </div>
  );
}

export default FavoriteList;
