import { useRef, useState } from 'react';
import { useNavigate } from 'react-router';
import axios_api from '../../../config/Axios';
import emptyProfile from '../../../src_assets/img/emptyProfile.png';
import DiaryTotalList from '../../diary/DiaryTotalList';
// create 시 or mount 시에 총 일기 개수, like 일기 개수 받아와야함.
// username, userMassage, profile 받아와야함
// 수정 시 파일 전송까지 ㅇㅇ
function ProfileForm() {
  // const accessToken =
  //   'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MiIsInVzZXJJZHgiOjIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpc3MiOiJuYW5hbCIsImlhdCI6MTY3NTA0ODIyOSwiZXhwIjoxNjc1MTM0NTU3fQ.2F4EjtoLvUugP4T06taB1QHh8izGwPS5UP2dk8-KjyogVwMb4nj-ELFn81edUAO9VEjoUtSzhn24iOWWHzh7IA';
  // axios_api.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

  // axios_api
  //   .get('user/profile')
  //   .then(({ data }) => {
  //     if (data.statusCode === 200) {
  //       if (data.data.ResponseMessage === '성공') {
  //         console.log(data.data.Profile);
  //       }
  //     }
  //   })
  //   .catch((err) => console.log(err));
  // 이미지 업로드를 위한 곳.
  const [Image, setImage] = useState(emptyProfile);
  const fileInput = useRef(null);

  const navigate = useNavigate();

  const onChange = (e) => {
    if (e.target.files[0]) {
      setImage(e.target.files[0]);
    } else {
      //업로드 취소할 시
      setImage(Image);
      return;
    }
    //화면에 프로필 사진 표시
    const reader = new FileReader();
    reader.onload = () => {
      if (reader.readyState === 2) {
        setImage(reader.result);
      }
    };
    reader.readAsDataURL(e.target.files[0]);
  };
  return (
    <div>
      <div className='flex justify-evenly mt-5'>
        <img
          src={Image}
          className='w-28 h-28 p-1 rounded-full'
          onClick={() => {
            fileInput.current.click();
          }}
        />
        <input
          type='file'
          style={{ display: 'none' }}
          accept='image/jpg,impge/png,image/jpeg'
          name='profile_img'
          onChange={onChange}
          ref={fileInput}
        />
        <div className='my-auto'>
          <p className='my-auto text-2xl font-bold p-1'>유저 님의 일기장</p>
          <span className='flex justify-end'>
            <button className=''>닉네임 변경</button>
          </span>
        </div>
      </div>
      <div className='my-3'>
        <p>유저가 설정하는 메시지</p>
        <button>메시지 수정</button>
      </div>
      <div className='flex justify-betweendd'>
        <p className='my-3 font-semibold'>일기 시작한지 N일째 입니다.</p>
        <button>수정하기</button>
      </div>
      <div className='flex justify-around box-border h-24 w-80 p-4 bg-slate-300/50'>
        <div
          className='grid content-evenly'
          onClick={() => navigate('/Diary/Total/List')}
        >
          <p className='text-center'>총 작성한 일기</p>
          <p className='text-center font-bold'>777</p>
        </div>
        <div className='grid content-evenly'>
          <p className='text-center'>좋아하는 일기</p>
          <p className='text-center font-bold'>32</p>
        </div>
      </div>
    </div>
  );
}

export default ProfileForm;
