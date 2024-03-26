import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginForm from './features/auth/LoginForm';
import RegisterForm from './features/auth/RegisterForm';
import RenewPasswordForm from './features/auth/RenewPasswordForm';
import ForgotPasswordForm from './features/auth/ForgotPasswordForm';
import RenewSuccess from './features/auth/RenewSuccess';
import HomePage from './pages/HomePage';
import {
    AuthPage,
    ProductPage,
    WareHousePage,
    ExportPage,
    ImportPage,
    CreateProductPage,
    EditProductPage
} from './pages';
import { Provider } from 'react-redux';
import { store } from './store/store';
import { Sidebar } from './layouts';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

const client = new QueryClient();

function App(): JSX.Element {
    return (
        <Provider store={store}>
            <QueryClientProvider client={client}>
                <BrowserRouter>
                    <div className="min-w-screen min-h-screen">
                        <Routes>
                            {/* auth route */}
                            <Route path="/auth" element={<AuthPage />}>
                                <Route path="login" element={<LoginForm />} />
                                <Route
                                    path="register"
                                    element={<RegisterForm />}
                                />
                                <Route
                                    path="forgot-password"
                                    element={<ForgotPasswordForm />}
                                />
                                <Route
                                    path="renew-password/:identifier"
                                    element={<RenewPasswordForm />}
                                />
                                <Route
                                    path="renew-success"
                                    element={<RenewSuccess />}
                                />
                            </Route>
                            {/* main route */}
                            <Route path="/" element={<Sidebar />}>
                                <Route index element={<HomePage />} />
                                <Route
                                    path="/warehouse"
                                    element={<WareHousePage />}
                                />
                                <Route
                                    path="/product"
                                    element={<ProductPage />}
                                />
                                <Route
                                    path="/product/create"
                                    element={<CreateProductPage />}
                                />
                                <Route
                                    path="/product/edit"
                                    element={<EditProductPage />}
                                />
                                <Route
                                    path="/export"
                                    element={<ExportPage />}
                                />
                                <Route
                                    path="/import"
                                    element={<ImportPage />}
                                />
                            </Route>
                        </Routes>
                    </div>
                </BrowserRouter>
            </QueryClientProvider>
        </Provider>
    );
}

export default App;
