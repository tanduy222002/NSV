import { render, screen } from '@testing-library/react';
import Loading from '../Loading';

describe('test Loading component', () => {
    it('should be displayed on screen', () => {
        render(<Loading />);
        const button = screen.getByRole('button');
        expect(button).toBeInTheDocument();
    });
});
