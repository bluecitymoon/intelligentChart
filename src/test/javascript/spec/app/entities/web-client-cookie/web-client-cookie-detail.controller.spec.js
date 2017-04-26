'use strict';

describe('Controller Tests', function() {

    describe('WebClientCookie Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWebClientCookie, MockWebsite, MockProxyServer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWebClientCookie = jasmine.createSpy('MockWebClientCookie');
            MockWebsite = jasmine.createSpy('MockWebsite');
            MockProxyServer = jasmine.createSpy('MockProxyServer');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'WebClientCookie': MockWebClientCookie,
                'Website': MockWebsite,
                'ProxyServer': MockProxyServer
            };
            createController = function() {
                $injector.get('$controller')("WebClientCookieDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:webClientCookieUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
