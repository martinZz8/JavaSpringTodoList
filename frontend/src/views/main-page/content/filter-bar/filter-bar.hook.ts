import {useState} from "react";

const useFilterBar = () => {
  const [isAddItemModalOpened, setIsAddItemModalOpened] = useState<boolean>(false);

  return {
    isAddItemModalOpened,
    setIsAddItemModalOpened
  };
};

export default useFilterBar;