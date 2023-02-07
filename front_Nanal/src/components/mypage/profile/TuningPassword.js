import { useState } from "react"
import downArrow from '../../../src_assets/img/arrow_drop_down.png'
import upArrow from '../../../src_assets/img/arrow_drop_up.png'


const TuningPassword = () => { 
  const [isClick, setIsClick] = useState(false)
  const changeClickTrue = () => {
    setIsClick(true)
  }
  const changeClickFalse = () => {
    setIsClick(false)
  }
  if (isClick === false) {
    return <div
      className='flex justify-between box-border border rounded-lg h-12 border-black indent-4 bg-emerald-200/75'
      onClick={changeClickTrue}
    >
      <div className="self-center">비밀번호 수정</div>
      <img src={downArrow} className='self-center' />
    </div>
  } else {
    return <div onClick={changeClickFalse}>
      <div
        className='flex justify-between box-border border rounded-lg h-12 border-black indent-4 bg-emerald-200/75'
        onClick={changeClickTrue}
      >
        <div className="self-center">비밀번호 수정</div>
        <img src={upArrow} className='self-center' />
      </div>
      <div>
        <p>현재 비밀번호를 입력해주세요.</p>
        <input type="text" />
        <p>변경할 비밀번호를 입력해주세요.</p>
        <input type="text" />
        <p>변경할 비밀번호를 한 번 더 입력해주세요.</p>
        <input type="text" />
      </div>
    </div>
  }
}

export default TuningPassword