import {useState, useRef, useCallback} from "react";

// interfaces
import type {IOption} from "./select.types";

// hooks
import useOnClickOutside from "../../../hooks/useOnClickOutside.hook";

const useSelect = (value: string, options: IOption[]) => {
  const [isSelectOpened, setIsSelectOpened] = useState<boolean>(false);
  const refSelect = useRef<HTMLDivElement>(null);
  useOnClickOutside(refSelect, useCallback(
    () => setIsSelectOpened(false),
    []
  ));

  const toggleSelectOpened = () => {
    setIsSelectOpened(prev => !prev);
  };

  const translateValue = (strToTranslate: string): string => {
    const foundIndex = options.findIndex(option => option.value === strToTranslate);

    if (foundIndex !== -1) {
      return options[foundIndex].textToShow;
    }

    return strToTranslate;
  };

  return {isSelectOpened, setIsSelectOpened, toggleSelectOpened, translateValue, refSelect};
};

export default useSelect;