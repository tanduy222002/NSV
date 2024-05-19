import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import PageLoading from '../PageLoading';

describe('PageLoading component', () => {
    test('renders without crashing', () => {
        render(<PageLoading />);
        const loadingText = screen.getByText('Đang xử lý');
        expect(loadingText).toBeInTheDocument();
    });
});
