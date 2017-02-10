(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonAreaPercentageDeleteController',PersonAreaPercentageDeleteController);

    PersonAreaPercentageDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonAreaPercentage'];

    function PersonAreaPercentageDeleteController($uibModalInstance, entity, PersonAreaPercentage) {
        var vm = this;

        vm.personAreaPercentage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonAreaPercentage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
