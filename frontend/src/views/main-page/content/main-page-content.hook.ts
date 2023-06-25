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

  // To delete
  useEffect(() => {
    console.log("items: ", items);
  },[items]);

  // -- Every time filterValues changes, refetch data from DB --
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
        "?",
        filterValues.name !== "" ? `name=${filterValues.name}&` : "",
        filterValues.isDoneStr !== "" ? `done=${filterValues.isDoneStr}&` : "",
        filterValues.isAscendingStr !== "" ? `asc=${filterValues.isAscendingStr}` : "",
      );

      if ((formedUrl[formedUrl.length-1] === "?") || (formedUrl[formedUrl.length-1] === "&")) {
        formedUrl = formedUrl.substring(0, formedUrl.length-1);
      }

      console.log("Formed url: ", formedUrl);

      setIsLoading(true);
      try {
        const {data} = await axios.get(formedUrl, {
          // headers: {
          //   "Content-Type": "application/json",
          //   "Access-Control-Allow-Origin": "*"
          // }
        });

        setItems(data.map((it: any) => ({
          id: it.id,
          name: it.name,
          description: it.description,
          createdOn: new Date(it.createdOn),
          done: it.done
        }) as IItem));

        setIsErrorDuringFetch(false);
      }
      catch(e: any) {
        setIsErrorDuringFetch(true);

        if (e.response) {
          // The request was made and the server responded with a status code
          // that falls out of the range of 2xx
          console.log("-- error response --");
          console.log(e.response.data);
          console.log(e.response.status);
          console.log(e.response.headers);
        } else if (e.request) {
          // The request was made but no response was received
          // `error.request` is an instance of XMLHttpRequest in the browser
          // and an instance of http.ClientRequest in node.js
          console.log("-- error request --");
          console.log(e.request);
        } else {
          // Something happened in setting up the request that triggered an Error
          console.log('-- other error --');
          console.log(e.message);
        }
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