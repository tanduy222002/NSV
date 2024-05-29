import { useState } from 'react';

const usePagination = (maxPage: number) => {
    const [currentPage, setCurrentPage] = useState(1);

    const goBack = () => setCurrentPage((cur) => (cur === 1 ? 1 : cur - 1));

    const goToPage = (targetPage: number) =>
        targetPage >= 1 && targetPage <= maxPage && setCurrentPage(targetPage);

    const goNext = () =>
        setCurrentPage((cur) => (cur === maxPage ? maxPage : cur + 1));

    return { currentPage, goToPage, goBack, goNext };
};

export default usePagination;
