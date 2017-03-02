'use strict';

describe('Controller Tests', function() {

    describe('PersonWordCloud Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonWordCloud, MockPerson, MockWordCloud;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonWordCloud = jasmine.createSpy('MockPersonWordCloud');
            MockPerson = jasmine.createSpy('MockPerson');
            MockWordCloud = jasmine.createSpy('MockWordCloud');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonWordCloud': MockPersonWordCloud,
                'Person': MockPerson,
                'WordCloud': MockWordCloud
            };
            createController = function() {
                $injector.get('$controller')("PersonWordCloudDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:personWordCloudUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
