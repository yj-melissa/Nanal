import { Cookies } from "react-cookie";

// Refresh Token은 브라우저 저장소(Cookie)에 store에 저장

const cookies = new Cookies();

// setRefreshToken : Refresh Token을 Cookie에 저장하기 위한 함수
export const setRefreshToken = (refreshToken) => {
  const today = new Date();
  const expireDate = today.setDate(today.getDate() + 7); // 유효기간 7일

  return cookies.set("refresh_token", refreshToken, {
    // SameSite를 Strict으로 설정하면 쿠키가 자사 컨텍스트에서만 전송
    sameSite: "strict",
    path: "/",
    expires: new Date(expireDate),
  });
};

// getCookieToken : Cookie에 저장된 Refresh Token 값을 갖고 오기 위한 함수.
export const getCookieToken = () => {
  return cookies.get("refresh_token");
};

// removeCookieToken : Cookie 삭제를 위한 함수. 로그아웃 시 사용 예정
export const removeCookieToken = () => {
  return cookies.remove("refresh_token", { sameSite: "strict", path: "/" });
};
