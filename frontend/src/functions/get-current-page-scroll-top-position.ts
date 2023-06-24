// Note: 'document.documentElement.scrollTop' always returns 0 on Chrome
export const getCurrentPageScrollTopPosition = () =>
  document.documentElement.scrollTop || document.body.scrollTop;