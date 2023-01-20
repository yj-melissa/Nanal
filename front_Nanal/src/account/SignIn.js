import React from "react";
import { Link } from "react-router-dom";
import SingUp from "./SignUp";

function SignIn() {
  return (
    <div>
      <div>
        <h1> SignIn to 나날</h1>
        <div id="sign-in-form">
          <form action="">
            <p>UserName</p>
            <input type="text" id="user-id" />
            <br />
            <p>Password</p>
            <input type="text" id="user-password" />
            <div>
              <Link to="{SingUp}">SignUp</Link>

              <button>SignIn</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default SignIn;
