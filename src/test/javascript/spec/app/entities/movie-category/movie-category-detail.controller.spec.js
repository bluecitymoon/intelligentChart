'use strict';

describe('Controller Tests', function() {

    describe('MovieCategory Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMovieCategory, MockMovie, MockAreaType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMovieCategory = jasmine.createSpy('MockMovieCategory');
            MockMovie = jasmine.createSpy('MockMovie');
            MockAreaType = jasmine.createSpy('MockAreaType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MovieCategory': MockMovieCategory,
                'Movie': MockMovie,
                'AreaType': MockAreaType
            };
            createController = function() {
                $injector.get('$controller')("MovieCategoryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intelligentChartApp:movieCategoryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
