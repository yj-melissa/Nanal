import React, { useState } from "react";
import { Link } from "react-router-dom";

function SignIn() {
  const [userId, setUserId] = useState("");
  const [pw, setPw] = useState("");

  const onChangeId = (e) => {
    setUserId(e.target.value);
  };

  const onChangePw = (e) => {
    setPw(e.target.value);
  };

  const onSubmit = (e) => {
    e.preventDefault();
  };

  return (
    <div>
      <div>
        <h1> SignIn to 나날</h1>
        <div id="sign-in-form">
          <form action="" onSubmit={onSubmit}>
            <label for="user-id">ID</label>
            <input
              type="text"
              id="user-id"
              placeholder="아이디"
              onChange={onChangeId}
              value={userId}
            />
            <br />
            <label for="user-password">PW</label>
            <input
              type="text"
              id="user-password"
              placeholder="비밀번호"
              onChange={onChangePw}
              value={pw}
            />
            <div>
              <button>Sign In</button>
              <br />
              <Link to="/SignUp">SignUp</Link>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default SignIn;
