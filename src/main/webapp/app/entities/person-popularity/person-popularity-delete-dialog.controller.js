(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonPopularityDeleteController',PersonPopularityDeleteController);

    PersonPopularityDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonPopularity'];

    function PersonPopularityDeleteController($uibModalInstance, entity, PersonPopularity) {
        var vm = this;

        vm.personPopularity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonPopularity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
