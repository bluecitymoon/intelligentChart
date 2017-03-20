(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('WordCloudDetailController', WordCloudDetailController);

    WordCloudDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WordCloud', 'PersonWordCloud'];

    function WordCloudDetailController($scope, $rootScope, $stateParams, previousState, entity, WordCloud, PersonWordCloud) {
        var vm = this;

        vm.wordCloud = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:wordCloudUpdate', function(event, result) {
            vm.wordCloud = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
