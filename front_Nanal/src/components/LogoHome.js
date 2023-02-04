import { Link } from "react-router-dom"
import nanal from '../src_assets/img/나날1.jpeg'

const LogoHome = () => { 
  return <div className="grid content-center">
    <img src={nanal} className="w-60 h-60 place-self-center"/>
    <Link to='/SignIn' className="place-self-center">로그인하러 가기</Link>
    <Link to='/SignUp' className="place-self-center">회원가입하러 가기</Link>
  </div>
}

export default LogoHome