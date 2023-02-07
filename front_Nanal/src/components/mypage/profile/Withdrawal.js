import axios_api from "../../../config/Axios"

const Withdrawal = () => {

  const signOut = (e) => {
    e.preventDefault()
    if (window.confirm('확인을 누르면 회원 정보가 삭제됩니다.')) {
      axios_api
      .get('user/profile')
      .then(({ data }) => {
        if (data.statusCode === 200) {
          if (data.data.responseMessage === '회원 정보 조회 성공') {
            console.log(data.data);
            axios_api
              .delete('user/profile')
              .then(() => {
                if (data.statusCode === 200) {
                  localStorage.clear();
                  alert('그동안 이용해주셔서 감사합니다.');
                  window.location.replace('/');
                } else { 
                  console.log('회원 탈퇴 오류: ');
                  console.log(data.statusCode)
                  console.log(data.data.responseMessage);
                }
              })
              .catch((err) => alert(err.response.data.message));
          }
        } else {
          console.log('회원 정보 오류: ');
          console.log(data.statusCode);
          console.log(data.data.responseMessage);
        }
      })
      .catch(({ error }) => {
        console.log('회원 정보 조회: ' + error);
      });
      
    }
  }
  return <div
    className='flex justify-between box-border border rounded-lg h-12 border-black indent-4 bg-red-500/50'
    onClick={signOut}
  > 
  <div className="self-center">회원 탈퇴</div>
</div>
}

export default Withdrawal