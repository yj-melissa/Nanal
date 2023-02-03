import { Link } from "react-router-dom";

const AlarmList = ({ content, creationDate, noticeType,  }) => {
  // console.log(content)
  return (
    <div>
      <p>{content}</p>
      <p>{creationDate}</p>
      <p>{noticeType}</p>
    </div>
  );
};

export default AlarmList;
