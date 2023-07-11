const isUserAuthenticated = (accessToken: string | null): boolean => {
  return (accessToken !== null) && (accessToken !== "");
};

export default isUserAuthenticated;