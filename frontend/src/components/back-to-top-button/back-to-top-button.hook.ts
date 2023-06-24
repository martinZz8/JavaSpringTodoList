import {useState, useEffect} from "react";

// functions
import {getCurrentPageScrollTopPosition} from "../../functions/get-current-page-scroll-top-position";

const useBackToTopButton = () => {
   const [isShowed, setIsShowed] = useState<boolean>(false);

   useEffect(() => {
      const handleScroll = () => {
         let valToSet: boolean = false;

         if (getCurrentPageScrollTopPosition() > 600) {
            valToSet = true;
         }

         setIsShowed(valToSet);
      };

      document.addEventListener("scroll", handleScroll);

      return () => {
        document.removeEventListener("scroll", handleScroll);
      };
   },[]);

   return {isShowed};
};

export default useBackToTopButton;