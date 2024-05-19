import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import UserInfo from '../UserInfo';
import { useAppSelector } from '@renderer/hooks';

jest.mock('@renderer/hooks', () => ({
    useAppSelector: jest.fn()
}));

describe('UserInfo component', () => {
    beforeEach(() => {
        const mockUser = { username: 'testuser' };
        (useAppSelector as jest.Mock).mockReturnValue(mockUser);
    });

    it('renders user information correctly', () => {
        render(<UserInfo />);

        expect(screen.getByText('testuser')).toBeInTheDocument();
        expect(screen.getByTestId('bell-icon')).toBeInTheDocument();
        expect(screen.getByTestId('arrow-icon')).toBeInTheDocument();
    });

    it('renders GoBell and MdKeyboardArrowDown icons', () => {
        render(<UserInfo />);

        const bellIcon = screen.getByTestId('bell-icon');
        const arrowIcon = screen.getByTestId('arrow-icon');

        expect(bellIcon).toBeInTheDocument();
        expect(bellIcon).toHaveClass('cursor-pointer');

        expect(arrowIcon).toBeInTheDocument();
        expect(arrowIcon).toHaveClass('cursor-pointer');
        expect(arrowIcon).toHaveClass('text-sky-700');
    });

    it('applies correct styles to the username container', () => {
        render(<UserInfo />);

        const usernameContainer = screen.getByText('testuser').parentElement;

        expect(usernameContainer).toHaveClass(
            'flex items-center gap-1 border border-sky-700 rounded-md font-semibold px-2 py-2 text-sky-700'
        );
    });
});
