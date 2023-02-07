import { useEffect } from "react"
import { onLogin } from '../../../config/Login';
import downArrow from '../../../src_assets/img/arrow_drop_down.png'
import TuningProfile from './TuningProfile'
import TuningUserInfo from './TuningUserInfo'
import TuningPassword from './TuningPassword'
import Withdrawal from './Withdrawal'

const Tuning = () => {
const tuningStyle = 'flex justify-between box-border border rounded-lg h-12 border-black indent-4 bg-emerald-200/75'

  useEffect(() => {
    onLogin()
  },[])
  
  return <div className="grid grid-cols-1 gap-4 place-content-evenly">
    {/* 프로필 수정 해야함*/}
    <TuningProfile />
    {/* PDF 미구현 */}
    <div className={tuningStyle} > 
      <div className="self-center">PDF로 내보내기</div>
    </div>
    {/* 유저정보 수정 해야함 */}
    <TuningUserInfo />
    {/* 비밀번호 수정 해야함 */}
    <TuningPassword />
    {/* 테마설정 미구현 */}
    <div className={tuningStyle} > 
      <div className="self-center">테마 설정</div>
      <img src={downArrow} className='self-center'/>
    </div>
    <hr className='my-4 w-80 border-slate-500/75' />
    {/* 회원 탈퇴 구현 완료*/}
    <Withdrawal />
  </div>
}

export default Tuning