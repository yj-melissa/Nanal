import { useEffect, useRef, useState } from 'react';
import axios_api from '../../../config/Axios';
import emptyProfile from '../../../src_assets/img/emptyProfile.png';
// create 시 or mount 시에 총 일기 개수, like 일기 개수 받아와야함.
// username, userMassage, profile 받아와야함
// 수정 시 파일 전송까지 ㅇㅇ
function ProfileForm() {
  // user profile
  const [userProfile, setUserProfile] = useState({
    img: null,
    introduction: null,
    nickname: '',
  });

  // diary information for user profile
  const [difup, setDifup] = useState({
    fromTheStartDay: null,
    allDiary: null,
    likeDiary: null,
  });
  // const accessToken =
  //   'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MTIzIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlzcyI6Im5hbmFsIiwiaWF0IjoxNjc1MjI5NDk2LCJleHAiOjE2NzUzMTQ3NjN9.sRmpr3WLBnD2KGaSEX8pYcxaNj_e8sKeWH5d8V0O_YI4RdqTkn-NsE3VqOCRYb_ldDFRq4QRUGnhKy93q_D7sg';
  // axios_api.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
  // // Mount 됐을 때 user
  // useEffect(() => {
  //   axios_api
  //     .get('user/profile')
  //     .then(({ data }) => {
  //       console.log('profile====');
  //       if (data.statusCode === 200) {
  //         if (data.data.responseMessage === '성공') {
  //           // console.log(data.data.profile);
  //           setUserProfile(data.data.profile);
  //         }
  //       }
  //     })
  //     .catch((err) => console.log(err));

  //   axios_api
  //     .get('diary/list/user')
  //     .then(({ data }) => {
  //       console.log('user====');
  //       console.log(data.data);
  //     })
  //     .catch((err) => console.log(err));
  // }, []);

  const [Image, setImage] = useState(
    userProfile.img === null ? emptyProfile : Image
  );
  const fileInput = useRef(null);
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
  // nickname

  // message

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
        <p className='my-3 font-semibold'>나날과 함께한 N일째 나날입니다.</p>
        <button>수정하기</button>
      </div>
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
