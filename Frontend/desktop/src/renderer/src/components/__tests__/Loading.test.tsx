import { render, screen } from '@testing-library/react';
import Loading from '../Loading';

describe('Loading Component Unit Test', () => {
    it('should be displayed on screen', () => {
        render(<Loading />);
        const loading = screen.getByTestId('loading-container');
        expect(loading).toBeInTheDocument();
    });
});
