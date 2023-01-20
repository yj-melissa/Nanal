function SignUp() {
  return (
    <div className="flex justify-center">
      <div className="box-border p-4 w-96 border-4 border-black">
        <h1 className="p-3">SignUp</h1>
        <form action="">
          <p for="email">Email</p>
          <div>
            <input type="text" id="email" />
            <button>인증요청</button>
          </div>
          <p for="user-id">userId</p>
          <input type="text" id="user-id" />
          <p for="password1">password1</p>
          <input type="text" id="password1" />
          <p for="password2">password2</p>
          <input type="text" id="password2" />
          {/* <p>UserName</p>
          <input type="text" /> */}
          <p for="user-nick-name">UserNickName</p>
          <input type="text" id="user-nick-name" />
          <br />
          <button>SignUp</button>
        </form>
      </div>
    </div>
  );
}

export default SignUp;
