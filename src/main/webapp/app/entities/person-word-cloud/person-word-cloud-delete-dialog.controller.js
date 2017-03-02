(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonWordCloudDeleteController',PersonWordCloudDeleteController);

    PersonWordCloudDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonWordCloud'];

    function PersonWordCloudDeleteController($uibModalInstance, entity, PersonWordCloud) {
        var vm = this;

        vm.personWordCloud = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonWordCloud.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
