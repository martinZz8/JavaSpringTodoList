import {useState, useEffect} from "react";
import axios from "axios";

// data
import {initialFilterValues} from "./main-page-content.data";

// interfaces
import {IFilterValues, IItem} from "./main-page-content.types";

const API_URL = process.env.REACT_APP_BACKEND_API_URL !== undefined ?
    `${process.env.REACT_APP_BACKEND_API_URL}todo_item/all_filtered`
  :
    null;

const useMainPageContent = () => {
  const [items, setItems] = useState<IItem[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [isErrorDuringFetch, setIsErrorDuringFetch] = useState<boolean>(false);
  const [filterValues, setFilterValues] = useState<IFilterValues>(initialFilterValues);

  useEffect(() => {
    void fetchData();
  },[filterValues]);

  const handleOnFilterChange = (name: string, value: string) => {
    setFilterValues(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const locallyDeleteItemAtIndex = (idx: number) => {
    if ((idx >= 0) && (idx < items.length)) {
        let newItems = [...items];
        newItems.splice(idx, 1);
        setItems(newItems);
    }
  };

  const fetchData = async () => {
    if (API_URL) {
      let formedUrl = API_URL.concat(
        '?',
        filterValues.name !== "" ? `name=${filterValues.name}&` : "",
        filterValues.isDoneStr !== "" ? `done=${filterValues.isDoneStr}&` : "",
        filterValues.isAscendingStr !== "" ? `asc=${filterValues.isAscendingStr}` : "",
      );

      if ((formedUrl[formedUrl.length-1] === "?") || (formedUrl[formedUrl.length-1] === "&")) {
        formedUrl = formedUrl.substring(0, formedUrl.length-1);
      }

      setIsLoading(true);
      try {
        const {data} = await axios.get(formedUrl);

        setItems(data.map((it: any) => ({
          id: it.id,
          name: it.name,
          description: it.description,
          createdOn: new Date(it.createdOn),
          done: it.done
        }) as IItem));

        setIsErrorDuringFetch(false);
      }
      catch(e) {
        // do some stuff here
        console.log("error during HTTP GET request");
        setIsErrorDuringFetch(true);
      }
      finally {
        setIsLoading(false);
      }
    }
  };

  // -- Not used --
  // const locallyChangeItemAtIndex = (idx: number, newItem: IItem) => {
  //   if ((idx >= 0) && (idx < items.length)) {
  //     setItems(prev => [
  //       ...prev.slice(0, idx),
  //       {
  //         id: newItem.id,
  //         name: newItem.name,
  //         description: newItem.description,
  //         createdOn: newItem.createdOn,
  //         done: newItem.done
  //       },
  //       ...prev.slice(idx+1, prev.length),
  //     ]);
  //   }
  // };

  // -- Not used --
  // const locallyAddItem = (newItem: IItem) => {
  //   setItems(prev => [
  //     ...prev,
  //     newItem
  //   ]);
  // };

  return {
    items,
    isLoading,
    isErrorDuringFetch,
    filterValues,
    handleOnFilterChange,
    fetchData
  };
};

export default useMainPageContent;