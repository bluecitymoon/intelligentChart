(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MovieCategoryDetailController', MovieCategoryDetailController);

    MovieCategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MovieCategory', 'Movie', 'AreaType'];

    function MovieCategoryDetailController($scope, $rootScope, $stateParams, previousState, entity, MovieCategory, Movie, AreaType) {
        var vm = this;

        vm.movieCategory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:movieCategoryUpdate', function(event, result) {
            vm.movieCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
