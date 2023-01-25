import React, { useRef, useReducer, useCallback, useMemo } from "react";
import DiaryEditor from "../components/DiaryEditor";
import DiaryList from "./DiaryList";

// 더미 데이터
// const dummyList = [
//   {
//     id: 1,
//     date: "",
//     content: "일기 1",
//     group: "private",
//     // 날짜는 숫자로 변환해서 받기
//     created_at: new Date().getTime(),
//   },
//   {
//     id: 2,
//     date: "",
//     content: "일기 2",
//     group: "group",
//     // 날짜는 숫자로 변환해서 받기
//     created_at: new Date().getTime(),
//   },
//   {
//     id: 3,
//     date: "",
//     content: "일기 3",
//     group: "private",
//     // 날짜는 숫자로 변환해서 받기
//     created_at: new Date().getTime(),
//   },
//   {
//     id: 4,
//     date: "",
//     content: "일기 4",
//     group: "group",
//     // 날짜는 숫자로 변환해서 받기
//     created_at: new Date().getTime(),
//   },
// ];

// 상태 관리 로직을 컴포넌트에서 분리
const reducer = (state, action) => {
  switch (action.type) {
    case "CREATE": {
      const created_at = new Date().getTime();
      const newItem = {
        ...action.data,
        created_at,
      };
      return [newItem, ...state];
    }
    case "REMOVE": {
      return state.filter((it) => it.id !== action.targetId);
    }
    case "EDIT": {
      return state.map((it) =>
        it.id === action.targetId ? { ...it, content: action.newContent } : it
      );
    }
    default:
      return state;
  }
};

// props drilling 문제를 해결하기 위함
export const DiaryStateContext = React.createContext();
// state를 변화시키는 함수는 별도로 context 생성하여 중첩사용 = 최적화가 풀리지 않게
export const DiaryDispatchContext = React.createContext();

function DiaryCreate() {
  // 일기 데이터는 부모 컴포넌트에서 관리 (데이터는 단방향으로 흐르기 떄문)
  // const [data, setData] = useState([]);
  // 상태 관리 로직을 컴포넌트에서 분리 - useReducer 사용
  const [data, dispatch] = useReducer(reducer, []);
  // id는 useRef로 생성
  const dataId = useRef(0);
  // 바뀌는 게 없다면 재렌더링 안되도록 useCallback 사용
  const onCreate = useCallback((content, group) => {
    dispatch({ type: "CREATE", data: { id: dataId.current, content, group } });
    // const created_at = new Date().getTime();
    // const newItem = {
    //   id: dataId.current,
    //   content,
    //   group,
    //   created_at,
    // };
    dataId.current += 1;
    // 새로운 일기를 가장 위로 - 함수를 전달해도 된다.
    // setData((data) => [newItem, ...data]);
  }, []);

  // 일기 삭제 함수 - id 다른 것들은 삭제에서 제외하여 새로운 배열로 표현
  const onRemove = useCallback((targetId) => {
    dispatch({ type: "REMOVE", targetId });
    // useCallback을 사용하기 위해 함수형으로 전달
    // setData((data) => data.filter((it) => it.id !== targetId));
  }, []);

  // 일기 수정 함수 - id 같은 것을 수정
  const onEdit = useCallback((targetId, newContent) => {
    dispatch({ type: "EDIT", targetId, newContent });
    // setData((data) =>
    //   data.map((it) =>
    //     it.id === targetId ? { ...it, content: newContent } : it
    //   )
    // );
  }, []);

  // 컴포넌트가 재생성되지 않도록 useMemo를 활용 = 하나의 값으로 묶어서 전달 예정
  const memoizedDispatches = useMemo(() => {
    return { onCreate, onRemove, onEdit };
  }, []);

  return (
    // context 로 래핑
    <DiaryStateContext.Provider value={data}>
      <DiaryDispatchContext.Provider value={memoizedDispatches}>
        <div>
          <h2>오늘의 일기</h2>
          <DiaryEditor />
          <DiaryList />
        </div>
      </DiaryDispatchContext.Provider>
    </DiaryStateContext.Provider>
  );
}

export default DiaryCreate;
