(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('WordCloudDeleteController',WordCloudDeleteController);

    WordCloudDeleteController.$inject = ['$uibModalInstance', 'entity', 'WordCloud'];

    function WordCloudDeleteController($uibModalInstance, entity, WordCloud) {
        var vm = this;

        vm.wordCloud = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WordCloud.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
