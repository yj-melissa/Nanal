import { useState } from "react"
import downArrow from '../../../src_assets/img/arrow_drop_down.png'

const ThemeSetting = () => {
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
      <div className="self-center">테마 설정</div>
      <img src={downArrow} className='self-center' />
    </div>
  } else {
    return <div>
        <div
        className='flex justify-between box-border border rounded-lg h-12 border-black indent-4 bg-emerald-200/75'
        onClick={changeClickFalse}
      >
        <div className="self-center">테마 설정</div>
        <img src={downArrow} className='self-center' />
      </div>
      <div>
        <p>미구현 된 기능입니다.</p>
        <p>1. 나의 라임 오렌지나무</p>
        <p>2. 노스텔지어의 손수건</p>
        <p>3. 발푸르기스의 밤</p>
        <p>4. 동물의 사육제</p>
      </div>
    </div>
    
  }
  
}

export default ThemeSetting