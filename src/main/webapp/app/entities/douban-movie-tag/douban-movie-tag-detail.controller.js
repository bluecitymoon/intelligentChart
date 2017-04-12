(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('DoubanMovieTagDetailController', DoubanMovieTagDetailController);

    DoubanMovieTagDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DoubanMovieTag'];

    function DoubanMovieTagDetailController($scope, $rootScope, $stateParams, previousState, entity, DoubanMovieTag) {
        var vm = this;

        vm.doubanMovieTag = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:doubanMovieTagUpdate', function(event, result) {
            vm.doubanMovieTag = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
