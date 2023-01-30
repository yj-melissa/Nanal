import { useRef, useState } from 'react';
import axios_api from '../../../config/Axios';
import emptyProfile from '../../../src_assets/img/emptyProfile.png';

function ProfileForm() {
  const accessToken =
    'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MiIsInVzZXJJZHgiOjIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpc3MiOiJuYW5hbCIsImlhdCI6MTY3NTA0ODIyOSwiZXhwIjoxNjc1MTM0NTU3fQ.2F4EjtoLvUugP4T06taB1QHh8izGwPS5UP2dk8-KjyogVwMb4nj-ELFn81edUAO9VEjoUtSzhn24iOWWHzh7IA';
  axios_api.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

  axios_api
    .get('user/profile')
    .then(({ data }) => {
      if (data.statusCode === 200) {
        if (data.data.ResponseMessage === '성공') {
          console.log(data.data.Profile);
        }
      }
    })
    .catch((err) => console.log(err));
  // 이미지 업로드를 위한 곳.
  const [Image, setImage] = useState(emptyProfile);
  const fileInput = useRef(null);
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
      <p className='my-3 font-semibold'>일기 시작한지 N일째 입니다.</p>
      <div className='flex justify-around box-border h-24 w-80 p-4 bg-slate-300/50'>
        <div className='grid content-evenly'>
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
