import {useState} from "react";

const useRoot = () => {
  const [appVersion, setAppVersion] = useState<string>("Todo list");

  return {appVersion};
};

export default useRoot;